import re
with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'r') as f:
    text = f.read()

text = text.replace(
'''            } catch (e: Exception) {
                panchangError.value = "Unable to load today's Panchang. Please check your connection or try again shortly."
                panchangTechnicalError.value = "Exception: ${e.message}"
            }
    }''',
'''            } catch (e: Exception) {
                panchangError.value = "Unable to load today's Panchang. Please check your connection or try again shortly."
                panchangTechnicalError.value = "Exception: ${e.message}"
            }
        }
    }''')

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'w') as f:
    f.write(text)
