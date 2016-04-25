##Gradle plugin to execute JMeter Tests.  [![Build Status](https://travis-ci.org/abutun/jmeter-gradle-plugin.svg?branch=master)](https://travis-ci.org/abutun/jmeter-gradle-plugin)

This plugin is a fork of http://jmeter.foragerr.net/ plugin

For usage see: http://jmeter.foragerr.net/ or [wiki](https://github.com/jmeter-gradle-plugin/jmeter-gradle-plugin/wiki/Getting-Started)

##News
**4/21/2016**
Version 1.0.3 released
* OptimizeXmlFiles property is added in case of malformed XML files.
* Bug fix for maxHeapSize property.
* added support for minHeapSize [#56](https://github.com/jmeter-gradle-plugin/jmeter-gradle-plugin/issues/56)
* added additional jmeter-plugin and webdriver jars to classpath [#57](https://github.com/jmeter-gradle-plugin/jmeter-gradle-plugin/issues/57)
* Fixed [#55](https://github.com/jmeter-gradle-plugin/jmeter-gradle-plugin/issues/55) issue with jmSystemPropertiesFiles
* Reformatted a few code files

##Attribution
This project started as a hard fork of [foragerr/jmeter-gradle-plugin](https://github.com/jmeter-gradle-plugin/jmeter-gradle-plugin). Besides defect fixes and feature enhancements, most of the original codebase has been re-written since. 

If you are a user of the older plugin see [here]() for easy migration to this version of the plugin. If you're a developer familiar with the older plugin, see [here]() for notes about major changes.
