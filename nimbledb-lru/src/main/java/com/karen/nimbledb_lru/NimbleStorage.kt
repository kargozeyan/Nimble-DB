package com.karen.nimbledb_lru

import java.io.Serializable

internal class NimbleStorage<T : Any>(var data: T) : Serializable