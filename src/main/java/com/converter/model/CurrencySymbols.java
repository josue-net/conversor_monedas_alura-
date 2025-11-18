
package com.converter.model;

import java.util.Map;

public class CurrencySymbols {
    private Map<String, String> symbols;

    public CurrencySymbols(Map<String, String> symbols) { this.symbols = symbols; }
    public Map<String, String> getSymbols() { return symbols; }
}
