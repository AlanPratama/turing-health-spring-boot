package com.turinghealth.turing.health;

import com.turinghealth.turing.health.service.HospitalServiceTest;
import com.turinghealth.turing.health.service.RegionServiceTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        HospitalServiceTest.class,
        RegionServiceTest.class
})
public class TestSuite {
}
