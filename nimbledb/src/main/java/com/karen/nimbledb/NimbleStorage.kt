package com.karen.nimbledb

import java.io.Serializable

internal class NimbleStorage<T : Any>(var data: T) : Serializable