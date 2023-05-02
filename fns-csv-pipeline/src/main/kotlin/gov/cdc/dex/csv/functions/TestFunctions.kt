package gov.cdc.dex.csv.functions

import com.microsoft.azure.functions.annotation.FunctionName
import com.microsoft.azure.functions.annotation.EventHubTrigger
import com.microsoft.azure.functions.ExecutionContext

/**
 * Azure Functions with event trigger.
 */
class TestFunctions {

    @FunctionName("Test_IngestListener")
    fun eventIngestion(
			@EventHubTrigger(
                name = "msg", 
                eventHubName = "%EventHubName_Ingest%",
                connection = "EventHubConnectionListen_Ingest") 
        message: String,
        context: ExecutionContext
    ) {
        context.logger.info("Ingest Event: "+message)
    }

    @FunctionName("Test_DecompressOkListener")
    fun eventDecompressOk(
			@EventHubTrigger(
                name = "msg", 
                eventHubName = "%EventHubName_DecompressOk%",
                connection = "EventHubConnectionListen_DecompressOk") 
        message: String,
        context: ExecutionContext
    ) {
        context.logger.info("Decompress Ok Event: "+message)
    }

    @FunctionName("Test_DecompressFailListener")
    fun eventDecompressFail(
			@EventHubTrigger(
                name = "msg", 
                eventHubName = "%EventHubName_DecompressFail%",
                connection = "EventHubConnectionListen_DecompressFail") 
        message: String,
        context: ExecutionContext
    ) {
        context.logger.info("Decompress Fail Event: "+message)
    }
}