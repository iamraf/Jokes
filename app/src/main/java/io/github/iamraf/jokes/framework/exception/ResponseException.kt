package io.github.iamraf.jokes.framework.exception

import java.lang.Exception

class ResponseException(override val message: String) : Exception()