import com.microsoft.azure.functions.*
import com.microsoft.azure.functions.annotation.*
import com.microsoft.azure.storage.blob.CloudBlobClient
import com.microsoft.azure.storage.blob.CloudBlobContainer
import com.microsoft.azure.storage.blob.ListBlobItem
import java.util.*

class BlobValidationFunction {
    @FunctionName("BlobValidationFunction")
    fun run(
        @BlobTrigger(name = "blob", path = "container-name/{folderName}", dataType = "binary") content: ByteArray,
        @BindingName("folderName") folderName: String,
        context: ExecutionContext
    ): String {
        val storageAccount = CloudStorageAccount.parse("<your-storage-connection-string>")
        val blobClient = storageAccount.createCloudBlobClient()
        val container = blobClient.getContainerReference("container-name")

        val invalidFiles = validateBlobContainer(container, folderName)
        return if (invalidFiles.isEmpty()) {
            "Validation passed."
        } else {
            "Validation failed. Non-CSV files found: ${invalidFiles.joinToString()}"
        }
    }

    private fun validateBlobContainer(container: CloudBlobContainer, folderName: String): List<String> {
        val invalidFiles = mutableListOf<String>()

        val folderReference = container.getDirectoryReference(folderName)
        val blobs: Iterable<ListBlobItem> = folderReference.listBlobs()

        for (blob in blobs) {
            if (blob is CloudBlobContainer) {
                val subfolderName = blob.prefix.removeSuffix("/")
                invalidFiles.addAll(validateBlobContainer(container, subfolderName))
            } else if (blob is CloudBlob) {
                val fileName = blob.name
                if (!fileName.endsWith(".csv")) {
                    invalidFiles.add(fileName)
                }
            }
        }

        return invalidFiles
    }
}
