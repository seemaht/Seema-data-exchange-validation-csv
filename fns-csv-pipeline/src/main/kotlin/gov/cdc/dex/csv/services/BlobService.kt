package gov.cdc.dex.csv.services

import com.azure.storage.blob.BlobClient
import java.io.InputStream
import java.io.OutputStream

interface BlobService{
    fun doesBlobExist(containerName:String, path:String):Boolean
    fun getBlobMetadata(containerName:String, path:String):Map<String,String>
    fun moveBlob(fromContainerName:String, fromPath: String, toContainerName:String, toPath: String):String
    fun getBlobDownloadStream(containerName:String, path:String):InputStream
    fun getBlobUploadStream(containerName:String, path:String):Pair<OutputStream,String>
}