package db

import sample.ResourceQuery
import sample.UserID
import java.util.*


interface DBReader {

    // if null --- return all queries
    fun getQueries(count: Int? = null): Collection<ResourceQuery>
}

// Writer also has read access
interface DBWriter : DBReader {

    fun updateQuery(queryId: Int, updatedState: ResourceQuery.State, datetime: Date): Unit
}


open class DBReaderImpl(protected val userID: UserID): DBReader {
    override fun getQueries(count: Int?): Collection<ResourceQuery> {
        TODO("not implemented")
    }
}

class DBWriterImpl(userID: UserID): DBWriter, DBReaderImpl(userID) {
    override fun updateQuery(queryId: Int, updatedState: ResourceQuery.State, datetime: Date) {
        TODO("not implemented")
    }

}

