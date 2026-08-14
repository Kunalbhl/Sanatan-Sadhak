sed -i -e '/^    override fun onDestroy() {$/,/^    }$/d' app/src/main/java/com/example/MainActivity.kt
cat << 'INNER_EOF' >> tmp_main.txt
    override fun onDestroy() {
        super.onDestroy()
        com.example.ui.components.AudioEngine.releaseAll()
    }
}
INNER_EOF
sed -i '$d' app/src/main/java/com/example/MainActivity.kt
cat tmp_main.txt >> app/src/main/java/com/example/MainActivity.kt
rm tmp_main.txt
