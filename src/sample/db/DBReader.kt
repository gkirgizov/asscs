package db

import sample.ResourceQuery
import sample.UserID
import java.util.*
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement

interface DBReader {

    // if null --- return all queries
    fun getQueries(count: Int?): Iterable<ResourceQuery>
    fun login() : Connection
}

// Writer also has read access
interface DBWriter : DBReader {

    fun updateQuery(queryId: Int, updatedState: ResourceQuery.State, datetime: Date): Unit
}


open class DBReaderImpl(protected val userID: UserID): DBReader {
    override fun login() : Connection {
        val url = "jdbc:postgresql://localhost/asscs"
        val properties = Properties()
        properties.setProperty("user", userID.toString())
        properties.setProperty("password", userID.toString())

        return DriverManager.getConnection(url, properties)
    }
    override fun getQueries(count: Int?): Iterable<ResourceQuery> {

        val connection = login()
        val st = connection.createStatement()
        //add using corresponding replicas based on userID
        val resultCount = if (count != null) " limit $count" else ""
        val query = "select * from public.\"ResourceQueries\"" + resultCount

        val res: ResultSet = st.executeQuery(query)
        var result: MutableList<ResourceQuery> = ArrayList()
        while (res.next()) {
            result.add(ResourceQuery(res))
        }

        return result
        TODO("not implemented")
    }
}

class DBWriterImpl(userID: UserID): DBWriter, DBReaderImpl(userID) {
    override fun updateQuery(queryId: Int, updatedState: ResourceQuery.State, datetime: Date) {

        val connection = login()
        val st = connection.createStatement()
        // if not RC then make it write to local file?
        val query = "update public.\"ResourceQueries\" set \"Status\" = '${updatedState.toString()}', " +
                "\"Updated\" = '${datetime.toString()}' where \"id\" = $queryId"
        st.execute(query)
    }

}

