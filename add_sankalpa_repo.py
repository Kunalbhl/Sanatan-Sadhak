with open("app/src/main/java/com/example/data/SadhakRepository.kt", "r") as f:
    text = f.read()

repo_code = """
    private val sankalpaDao by lazy { db.sankalpaDao() }
    
    fun getSankalpaLogs(): kotlinx.coroutines.flow.Flow<List<SankalpaLog>> = sankalpaDao.getAllLogs()
    suspend fun addSankalpaLog(log: SankalpaLog) = sankalpaDao.insertLog(log)
"""

if "sankalpaDao" not in text:
    text = text.replace("    private val commentDao by lazy { db.postCommentDao() }", "    private val commentDao by lazy { db.postCommentDao() }\n" + repo_code)
    
    with open("app/src/main/java/com/example/data/SadhakRepository.kt", "w") as f:
        f.write(text)
