package com.fomo.builders;

class ChildBuilder<T> {
    final T parentBuilder;

    ChildBuilder(T parentBuilder) {
        this.parentBuilder = parentBuilder;
    }

    public T build() {
        return parentBuilder;
    }
}
