test:
	./gradlew clean cleanTest test 2>/dev/null | grep -v '.*]' | grep -v '^:' | grep -v 'single directory'
