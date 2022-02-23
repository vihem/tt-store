package com.taotao.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.apache.commons.lang3.StringUtils;


public class Test {
	public static void main(String[] args) {
		String ids = "1,2,3,4,5";

		String[] splitIds = ids.split(",");
		List<Long> idList = new ArrayList<Long>();
		for (int i = 0; i < splitIds.length; i++) {
			idList.add(Long.parseLong(splitIds[i]));
		}
		
//		List<Long> idList = Arrays.asList(ids.split(","))
//				.stream()
//				.map(s -> Long.parseLong(s.trim()))
//				.collect(Collectors.toList());
		System.out.println(idList);
		
		String string = null;
		if (StringUtils.isNotBlank(string)) {
			System.out.println("string "+string);
		}
		
		/**
		 * 
		isNotBlank(CharSequence cs)
		
		Checks if a CharSequence is not empty (""), not null and not whitespace only.
		 StringUtils.isNotBlank(null)      = false
		 StringUtils.isNotBlank("")        = false
		 StringUtils.isNotBlank(" ")       = false
		 StringUtils.isNotBlank("bob")     = true
		 StringUtils.isNotBlank("  bob  ") = true
		 
		Parameters:cs the CharSequence to check, 
		may be nullReturns:true if the CharSequence isnot empty and not null and not whitespace
		 */
		
		
	}
}
