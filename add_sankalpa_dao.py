with open("app/src/main/java/com/example/data/AppDatabase.kt", "r") as f:
    text = f.read()

dao_code = """
@Dao
interface SankalpaDao {
    @Query("SELECT * FROM sankalpa_logs ORDER BY timestamp DESC")
    fun getAllLogs(): kotlinx.coroutines.flow.Flow<List<SankalpaLog>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: SankalpaLog)
}
"""

if "SankalpaDao" not in text:
    text = text.replace("@Database(entities = [", "@Database(entities = [SankalpaLog::class, ")
    text = text + dao_code
    text = text.replace("abstract fun chatDao(): ChatDao", "abstract fun chatDao(): ChatDao\n    abstract fun sankalpaDao(): SankalpaDao")
    
    with open("app/src/main/java/com/example/data/AppDatabase.kt", "w") as f:
        f.write(text)
