/*
MIT No Attribution

Copyright (c) 2022 Dennis Katz

Permission is hereby granted, free of charge, to any person obtaining a copy of this
software and associated documentation files (the "Software"), to deal in the Software
without restriction, including without limitation the rights to use, copy, modify,
merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
permit persons to whom the Software is furnished to do so.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import java.security.InvalidParameterException;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

/**
 * Converts a given RQL like Sort-String to a Spring-Data Sort-Object.
 * RQL: https://www.eclipse.org/ditto/basic-rql.html#rql-sorting
 * 
 * @author Dennis Katz
 *
 */
public class StringToSortConverter implements Converter<String, Sort> {
	
	public Sort convert(String source) {
		if(!source.matches("^([+\\- ][a-zA-Z_$][a-zA-Z0-9_$]*,?)+$")) {
			throw new InvalidParameterException("invalid sort parameter format for input string: '"+source+"'");
		}
		String[] parts = source.split(",");
		Order[] orders = new Order[parts.length];
		for(int i = 0; i < parts.length; i++) {
			orders[i] = new Order(!parts[i].startsWith("-") ? Direction.ASC : Direction.DESC, parts[i].matches("^[+\\- ]") ? parts[i].substring(1) : parts[i]);
		}
		return Sort.by(orders);
	}
}
