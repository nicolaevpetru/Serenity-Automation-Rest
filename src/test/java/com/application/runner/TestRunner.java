package com.application.runner;


import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("API Automation Suit")
@SelectPackages("com.application.tests")
@IncludeTags("library")
//@IncludeTags( {"Sprint1","regression"} )

public class TestRunner {

}