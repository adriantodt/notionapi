package net.adriantodt.notionapi.net

import java.time.Duration

interface Request {
    var timeout: Duration?
}
