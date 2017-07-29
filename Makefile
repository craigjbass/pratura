test:
	./gradlew clean test 2>/dev/null | grep -v '.*]' | grep -v '^:' | grep -v 'single directory'
