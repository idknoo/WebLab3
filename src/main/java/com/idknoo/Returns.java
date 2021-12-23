package com.idknoo;

public enum Returns {
    TO_MAIN{
        @Override
        public String toString() {
            return "toMain";
        }
    },
    TO_START{
        @Override
        public String toString() {
            return "toStart";
        }
    }
}
