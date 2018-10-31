package com.mylearning.controller

import com.mylearning.delegate.MasterDelegate
import com.mylearning.exception.InValidDataException
import spock.lang.Specification
import spock.lang.Unroll

class SimulatorControllerSpec extends Specification {

    def delegate = Mock(MasterDelegate)
    def operations = Mock(InputStream)
    def controller = new SimulatorController(masterDelegate: delegate);
    def args = new String[2];

    @Unroll
    def "RunSimulation - with invalid sitemap arg and it exits without exception"() {
        given:
        args[0] = files

        when:
        controller.runSimulation(args, operations)

        then:
        1 * delegate.setupSimulator(_) >> { throw new InValidDataException("E1001","Invalid file") }
        noExceptionThrown()

        where:
        scenario | files         | operationsw
        1        | "siteMaptt"   | "q"
        2        | null          | "r"
        3        | "\\abc\\site" | "l"
    }

    def "RunSimulation - handles any exception"() {
        when:
        controller.runSimulation(null, operations)

        then:
        1 * delegate.setupSimulator(_) >> new Exception("exception")
        noExceptionThrown()
    }

    @Unroll
    def "RunSimulation - handles operations"() {
        given:
        args[0] = "siteMap"
        def input = new ByteArrayInputStream(operationInput.getBytes("UTF-8"))

        when:
        controller.runSimulation(args, input)

        then:
        1 * delegate.setupSimulator(_)
        1 * delegate.executeOperation(_ as Scanner) >> true
        noExceptionThrown()

        where:
        scenario|operationInput
        1|"q"
        2|"A"
    }


    @Unroll
    def "ServiceOperations handles terminal conditions"() {
        given:
        def input = new Scanner(operationsData)

        when:
        controller.serviceOperations(input)

        then:
        count * delegate.executeOperation(_ as Scanner) >> result

        then:
        noExceptionThrown();

        where:
        scenario | operationsData | count | result
        1        | "dgfb"         | 1     | { throw new InValidDataException("E1001","Invalid") }
        2        | "q"            | 1     | Boolean.TRUE
        3        | ""             | 0     | { throw new InValidDataException("E1001","Invalid") }

    }

    def "ServiceOperations handles Exceptions with ShutDown"() {
        given:
        def input = new Scanner("dgfb")
        delegate.executeOperation(_ as Scanner) >> { throw new InValidDataException("E1001","UnSupported Operation") }

        when:
        controller.serviceOperations(input)

        then:
        1 * delegate.shutDownSimulatorOnError(_ as String)

        then:
        noExceptionThrown();
    }
}
