package br.com.marquesapps.branchdownloader.downloader

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import br.com.marquesapps.branchdownloader.model.Branch
import br.com.marquesapps.branchdownloader.model.Repository

private const val BASE_URL = "https://api.github.com/repos/"
private const val HEADER_ACCEPT = "Accept"
private const val DOWNLOAD_TYPE = "zipball"
private const val DOWNLOAD_FORMAT = "zip"
private const val MIME_JSON_GITHUB = "application/vnd.github.v3+json"
private const val MESSAGE_DESCRIPTION = "Downloading"

class BranchDownloader(
    private val context: Context
) {

    private val manager: DownloadManager by lazy {
        context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    }

    fun download(repository: Repository, branch: Branch) {
        val uri = Uri
            .parse("$BASE_URL${repository.owner}/${repository.name}/$DOWNLOAD_TYPE/${branch.name}")
        val request = DownloadManager.Request(uri).apply {
            addRequestHeader(HEADER_ACCEPT, MIME_JSON_GITHUB)
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            setTitle(branch.name)
            setDescription("$MESSAGE_DESCRIPTION ${branch.name}")
            setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS, "${branch.name}.$DOWNLOAD_FORMAT"
            )
        }
        manager.enqueue(request)
    }

}