import re
with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'r') as f:
    text = f.read()

refresh_vars = '''
    private val _isRefreshing = kotlinx.coroutines.flow.MutableStateFlow(false)
    val isRefreshing: kotlinx.coroutines.flow.StateFlow<Boolean> = _isRefreshing

    fun refreshHome() {
        viewModelScope.launch {
            _isRefreshing.value = true
            loadDailyPanchangAndThought()
            kotlinx.coroutines.delay(1000)
            _isRefreshing.value = false
        }
    }
    
    fun refreshGeneric() {
        viewModelScope.launch {
            _isRefreshing.value = true
            kotlinx.coroutines.delay(1000)
            _isRefreshing.value = false
        }
    }
'''

text = re.sub(r'(class SadhakViewModel.*?\{)', r'\1\n' + refresh_vars, text, count=1)

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'w') as f:
    f.write(text)
