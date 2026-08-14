import re

with open('app/src/main/java/com/example/ui/viewmodel/HomeViewModel.kt', 'r') as f:
    hvm_content = f.read()

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'r') as f:
    svm_content = f.read()

# Extract the shuffling block from SVM
shuffle_block_regex = r'(// 1\. Select random Shloka of the day[\s\S]*?)val listEn = listOf\('
match = re.search(shuffle_block_regex, svm_content)
if match:
    shuffling_code = match.group(1)
    
    # In HomeViewModel, replace the init block contents with the shuffling code
    hvm_content = re.sub(r'init \{[\s\S]*?\}', 'init {\n        shuffleDailyContent()\n    }\n\n    fun shuffleDailyContent() {\n        ' + shuffling_code.replace('\n', '\n        ') + '\n    }', hvm_content)
    
    # Remove from SadhakViewModel
    svm_content = svm_content.replace(shuffling_code, '')

with open('app/src/main/java/com/example/ui/viewmodel/HomeViewModel.kt', 'w') as f:
    f.write(hvm_content)

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'w') as f:
    f.write(svm_content)
