with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'r') as f:
    text = f.read()
print('open:', text.count('{'), 'close:', text.count('}'))
