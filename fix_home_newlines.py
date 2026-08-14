with open('app/src/main/java/com/example/ui/viewmodel/HomeViewModel.kt', 'r') as f:
    content = f.read()

content = content.replace('कदाचन।\nमा कर्मफल', 'कदाचन।\\nमा कर्मफल')
content = content.replace('भारत।\nअभ्युत्थानम', 'भारत।\\nअभ्युत्थानम')
content = content.replace('दुष्कृताम्।\nधर्मसंस्थापनार्थाय', 'दुष्कृताम्।\\nधर्मसंस्थापनार्थाय')

with open('app/src/main/java/com/example/ui/viewmodel/HomeViewModel.kt', 'w') as f:
    f.write(content)
