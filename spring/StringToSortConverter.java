public class StringToSortConverter implements Converter<String, Sort> {
	
	public Sort convert(String source) {
		System.out.println(source);
		if(!source.matches("^([+\\- ][a-zA-Z_$][a-zA-Z0-9_$]*,?)+$")) {
			throw new InvalidParameterException("invalid sort parameter format for input string: '"+source+"'");
		}
		String[] parts = source.split(",");
		Order[] orders = new Order[parts.length];
		for(int i = 0; i < parts.length; i++) {
			System.out.println(parts[i]);
			orders[i] = new Order(!parts[i].startsWith("-") ? Direction.ASC : Direction.DESC, parts[i].matches("^[+\\- ]") ? parts[i].substring(1) : parts[i]);
		}
		return Sort.by(orders);
	}
}
