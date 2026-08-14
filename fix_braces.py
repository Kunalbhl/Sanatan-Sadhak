with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'r') as f:
    text = f.read()

import re
text = re.sub(r'\}+\s*$', '', text)

# We want 339 close braces to match 339 open braces.
# Actually, the best way is to let Android Studio format or something, but we can just count.
# The previous count was open: 339, close: 341.
# And the file ends with `}}}}}` (which is 5 close braces).
# So if we strip all trailing `}`, we will have some number.
# Let's see:
text = re.sub(r'(\s*\})+\s*$', '', text)
open_c = text.count('{')
close_c = text.count('}')
text += '\n' + '}' * (open_c - close_c) + '\n'

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'w') as f:
    f.write(text)
