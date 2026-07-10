def trace_braces(file_path):
    with open(file_path, "r") as f:
        lines = f.readlines()

    count = 0
    in_lazy_col = False
    lazy_col_depth = 0
    
    for i, line in enumerate(lines):
        if "LazyColumn(" in line and "fun HomeScreen" in "".join(lines[i-15:i]):
            in_lazy_col = True
            lazy_col_depth = count
            print(f"LazyColumn starts at line {i+1}, depth {count}")
            
        count += line.count('{') - line.count('}')
        
        if in_lazy_col and count <= lazy_col_depth:
            print(f"LazyColumn ends at line {i+1}")
            in_lazy_col = False

trace_braces("app/src/main/java/com/example/ui/screens/AllScreens.kt")
