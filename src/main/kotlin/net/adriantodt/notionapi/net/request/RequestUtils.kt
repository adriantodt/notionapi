package net.adriantodt.notionapi.net.request

import net.adriantodt.notionapi.model.database.Database
import net.adriantodt.notionapi.model.page.Page
import net.adriantodt.notionapi.model.page.PageParent

fun Database.toParent(): PageParent = PageParent.Database(id)

fun Page.toParent(): PageParent = PageParent.Page(id)
