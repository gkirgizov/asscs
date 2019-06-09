package db

import sample.ResourceQuery
import sample.UserID
import java.util.*


interface DBReader {

    // if null --- return all queries
    fun getQueries(count: Int?): Iterable<ResourceQuery>
}

interface DBWriter {

    fun updateQuery(queryId: Int, updatedState: ResourceQuery.State, datetime: Date): Unit
}


class DBReaderImpl(private val userID: UserID): DBReader {
    override fun getQueries(count: Int?): Iterable<ResourceQuery> {
        TODO("not implemented")
    }
}

class DBWriterImpl(private val userID: UserID): DBWriter {
    override fun updateQuery(queryId: Int, updatedState: ResourceQuery.State, datetime: Date) {
        TODO("not implemented")
    }

}

