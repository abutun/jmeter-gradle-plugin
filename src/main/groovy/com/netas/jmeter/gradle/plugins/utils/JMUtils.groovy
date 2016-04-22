package com.netas.jmeter.gradle.plugins.utils

import groovy.util.logging.Slf4j
import org.apache.tools.ant.DirectoryScanner
import org.gradle.api.GradleException
import org.gradle.api.Project

import java.text.DateFormat
import java.text.SimpleDateFormat

/**
 * Created by foragerr@gmail.com on 7/19/2015.
 */
@Slf4j
class JMUtils {

    static List<File> getListOfTestFiles(Project project){
        List<File> testFiles = new ArrayList<File>();
        if (project.jmeter.jmTestFiles != null) {
            project.jmeter.jmTestFiles.each { File file ->
                if (file.exists() && file.isFile()) {
                    testFiles.add(file);
                } else {
                    throw new GradleException("Test file " + file.getCanonicalPath() + " does not exists");
                }
            }
        } else {
            String[] excludes = project.jmeter.excludes == null ?  [] as String[] : project.jmeter.excludes as String[];
            String[] includes = project.jmeter.includes == null ? ["**/*.jmx"] as String[] : project.jmeter.includes as String[];
            log.info("includes: " + includes)
            log.info("excludes: " + excludes)
            testFiles.addAll(JMUtils.scanDir(project, includes, excludes, project.jmeter.testFileDir));
            log.info(testFiles.size() + " test files found in folder scan")
        }

        return testFiles;
    }

    static File getJmeterPropsFile(Project project) {
        File propsInSrcDir = new File(project.jmeter.testFileDir,"jmeter.properties");

        //1. Is jmeterPropertyFile defined?
        if (project.jmeter.jmPropertyFile != null)
            return project.jmeter.jmPropertyFile;

        //2. Does jmeter.properties exist in $srcDir/test/jmeter
        else if (propsInSrcDir.exists())
            return propsInSrcDir;

        //3. If neither, use the default jmeter.properties
        else{
            File defPropsFile = new File(project.jmeter.workDir, System.getProperty("default_jm_properties"));
            return defPropsFile;
        }
    }

	static File getResultFile(File testFile, Project project) {
		DateFormat fmt = new SimpleDateFormat("yyyyMMdd-HHmm");
		if (project.jmeter.resultFilenameTimestamp==null)
			return new File(project.jmeter.reportDir, testFile.getName() + "-" + fmt.format(new Date()) + ".xml");

		//if resultFilenameTimestamp is "none" do not use a timestamp in filename
		if (project.jmeter.resultFilenameTimestamp.equals("none"))
			return new File(project.jmeter.reportDir, testFile.getName() + ".xml");

		//else if resultFilenameTimestamp is "useSaveServiceFormat" use saveservice.format
		if (project.jmeter.resultFilenameTimestamp.equals("useSaveServiceFormat")){
			String saveServiceFormat =  System.getProperty("jmeter.save.saveservice.timestamp_format");
			if (saveServiceFormat.equals("none")) return new File(project.jmeter.reportDir, testFile.getName() + ".xml");
			try
			{
				fmt = new SimpleDateFormat(saveServiceFormat);
				return new File(project.jmeter.reportDir, testFile.getName() + "-" + fmt.format(new Date()) + ".xml");
			}
			catch (Exception e)
			{
				// jmeter.save.saveservice.timestamp_format does not contain a valid format
				log.warn("jmeter.save.saveservice.timestamp_format Not defined, using default timestamp format");
			}
		}
        //for all other unhandled conditions fallback to default:
        fmt = new SimpleDateFormat("yyyyMMdd-HHmm");
        return new File(project.jmeter.reportDir, testFile.getName() + "-" + fmt.format(new Date()) + ".xml");
    }

	
    static  List<File> scanDir(Project project, String[] includes, String[] excludes, File baseDir) {
        List<File> scanResults = new ArrayList<File>()
        DirectoryScanner scanner = new DirectoryScanner()
        scanner.setBasedir(baseDir)
        scanner.setIncludes(includes)
        scanner.setExcludes(excludes)
        scanner.scan()
        for (String result : scanner.getIncludedFiles()) {
            scanResults.add(new File(scanner.getBasedir(), result))
        }
        return scanResults;
    }
}
