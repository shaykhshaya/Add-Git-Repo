package com.shaya.githubrepository.network.gitresponse


data class GitResponse(
    val description: String?,
    val downloads_url: String?,
    val git_url: String?,
    val hooks_url: String?,
    val html_url: String?,
    val mirror_url: Any?,
    val name: String?,
    val owner: Owner?,
    val subscribers_url: String?,
    val subscription_url: String?,
    val svn_url: String?,
    val tags_url: String?,
    val teams_url: String?,
    val temp_clone_token: Any?,
    val topics: List<Any>?,
    val trees_url: String?,
    val updated_at: String?,
    val url: String?
)