package br.com.sscode.wallpaperpexelapp.framework.downloader

interface Downloader {
    fun downloadFile(url: String, description: String): Long
}
