#ifndef RESULT_H
#define RESULT_H

#include <iostream>
#include <vector>
#include <memory>
#include <stdexcept>
using namespace std;

template <typename T, typename E> 
class Result {
public:
	Result();
	Result(const T& value);
	Result(E* error);
	Result(vector<E*>& errors);
	Result(const Result<T, E>& other);

	~Result();

	static Result<T, E> ok(const T& value);
	static Result<T, E> fail(E* error);
	static Result<T, E> fail(vector<E*>& errors);

	Result<T, E>& operator=(const Result<T, E>& other);

	bool isSuccess();

	T getValue();
	vector<E*>& getErrors();

private:
	T* value;
	vector<E*> errors;
	bool is_success;

};

template <typename T, typename E> 
Result<T, E>::Result() 
	: value(nullptr), errors(), is_success(true) {}

template <typename T, typename E> 
Result<T, E>::Result(const T& value) 
	: value(new T(value)), errors(), is_success(true) {}

template <typename T, typename E>
Result<T, E>::Result(E* err) 
	: value(nullptr), errors({err}), is_success(false) {}

template <typename T, typename E>
Result<T, E>::Result(vector<E*>& errs) 
	: value(nullptr), errors(errs), is_success(false) {}

template <typename T, typename E>
Result<T, E>::Result(const Result<T, E>& other) 
	: value(other.is_success ? new T(*other.value) : nullptr), errors(other.errors), is_success(other.is_success) {}

template <typename T, typename E>
Result<T, E>::~Result() {
	if (value) {
		delete value;
	}
	for (auto error : errors) {
		delete error;
	}
}

template <typename T, typename E>
Result<T, E> Result<T, E>::ok(const T& value) {
	return Result<T, E>(value);
}

template <typename T, typename E>
Result<T, E> Result<T, E>::fail(E* error) {
	return Result<T, E>(error);
}

template <typename T, typename E>
Result<T, E> Result<T, E>::fail(vector<E*>& errors) {
	//return Result<T, E>(errors);
	Result<T, E> res;
	res.is_success = false;
	
	for (const auto& err : errors) {
		res.errors.push_back(new E(*err));
	}
	
	return res;
}

template <typename T, typename E>
Result<T, E>& Result<T, E>::operator=(const Result<T, E>& other) {
	if (this != &other) {
		if (value)
			delete value;
		for (auto err : errors)
			delete err;
		errors.clear();

		value = other.is_success ? new T(*other.value) : nullptr;
		for (auto err : other.errors) {
			errors.push_back(new E(*err));
		}
		is_success = other.is_success;
	}
	return *this;
}

template <typename T, typename E>
bool Result<T, E>::isSuccess() {
	return is_success;
}

template <typename T, typename E>
T Result<T, E>::getValue() {
	if (!is_success)
		return NULL;
		//throw runtime_error("Attempted to get value from a failed Result.");
	return *value;
}

template <typename T, typename E>
vector<E*>& Result<T, E>::getErrors() {
	//if (is_success)
	//	throw runtime_error("Attempted to get errors from a successful Result.");
	return errors;
}

// ------------------------------------------------------------------------------------

template <typename E>
class Result<void, E> {
public:
	Result();
	Result(E* error);
	Result(vector<E*>& errors);
	Result(const Result<void, E>& other);

	~Result();

	static Result<void, E> ok();
	static Result<void, E> fail(E* error);
	static Result<void, E> fail(vector<E*>& errors);

	Result<void, E>& operator=(const Result<void, E>& other);

	bool isSuccess();

	vector<E*>& getErrors();

private:
	vector<E*> errors;
	bool is_success;

};

template <typename E>
Result<void, E>::Result() 
	: errors(), is_success(true) {}

template <typename E>
Result<void, E>::Result(E* error) 
	: errors({error}), is_success(false) {}

template <typename E>
Result<void, E>::Result(vector<E*>& errors) 
	: errors(errors), is_success(false) {}

template <typename E>
Result<void, E>::Result(const Result<void, E>& other) 
	: errors(other.errors), is_success(other.is_success) {}

template <typename E>
Result<void, E>::~Result() {
	for (auto error : errors) {
		delete error;
	}
}

template <typename E>
Result<void, E> Result<void, E>::ok() {
	return Result<void, E>();
}

template <typename E>
Result<void, E> Result<void, E>::fail(E* error) {
	return Result<void, E>(error);
}

template <typename E>
Result<void, E> Result<void, E>::fail(vector<E*>& errors) {
	//return Result<void, E>(errors);
	Result<void, E> res;
	res.is_success = false;

	
	for (const auto& err : errors) {
		res.errors.push_back(new E(*err));
	}

	return res;
}

template <typename E>
Result<void, E>& Result<void, E>::operator=(const Result<void, E>& other) {
	if (this != &other) {
		for (auto err : errors) {
			delete err;
		}
		errors.clear();
		
		for (auto err : other.errors) {
			errors.push_back(new E(*err));
		}
		is_success = other.is_success;
	}
	return *this;
}

template <typename E>
bool Result<void, E>::isSuccess() {
	return is_success;
}

template <typename E>
vector<E*>& Result<void, E>::getErrors() {
	//if (is_success)
	//	throw runtime_error("Attempted to get errors from a successful Result.");
	return errors;
}

#endif