bundle:
	@mkdir -p build
	@javac lib/**/*.java -d build
	@jar cvf lib.jar -C build .
	@rm -rf build

.PHONY: bundle
