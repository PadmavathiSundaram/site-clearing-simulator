package com.mylearning.controller

import com.mylearning.delegate.MasterDelegate
import spock.lang.Specification

class GlobalExceptionControllerSpec extends Specification {
    def "UncaughtException"() {
        given:
        def delegate = Mock(MasterDelegate)
        def globalExceptionController = new GlobalExceptionController(masterDelegate:delegate )
        def e = new Exception("Exception")
        when:
        globalExceptionController.uncaughtException(Mock(Thread), e)

        then:
        1 * delegate.shutDownSimulatorOnError(e.getMessage())

    }
}
