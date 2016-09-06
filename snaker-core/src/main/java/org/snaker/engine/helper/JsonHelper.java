/* Copyright 2013-2015 www.snakerflow.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.snaker.engine.helper;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * json处理帮助类
 * @author yuqs
 * @since 1.0
 */
public class JsonHelper {
//	private static final Logger log = LoggerFactory.getLogger(JsonHelper.class);
	/**
	 * jackson的ObjectMapper对象
	 */
	private static ObjectMapper mapper = new ObjectMapper();
	/**
	 * 将对象转换为json字符串
	 * @param object
	 * @return
	 */
	public static String toJson(Object object) {
		if(object == null) return "";
		try {
			return mapper.writeValueAsString(object);
		} catch (Exception e) {
			System.out.println(e);
//			log.warn("write to json string error:" + object, e);
			return "";
		}
	}
	
	/**
	 * 根据指定类型解析json字符串，并返回该类型的对象
	 * @param jsonString
	 * @param clazz
	 * @return
	 */
	public static <T> T fromJson(String jsonString, Class<T> clazz) {
		if (StringHelper.isEmpty(jsonString)) {
			return null;
		}

		try {
			return mapper.readValue(jsonString, clazz);
		} catch (Exception e) {
			System.out.println(e);
//			log.warn("parse json string error:" + jsonString, e);
			return null;
		}
	}

	/*测试jsonHelper方法，注意的是：
	1、对象的属性需要有getter方法，或者在对象的属性上面加上@JsonProperty注解，否则会出现转换异常
	2、反序列化的时候需要对象有无参数构造方法才ok*/

	public static void main(String[] args) {
		Student student = new Student("qinys","23");
		System.out.println(student);
		Teacher teacher = new Teacher("xia","16");
		System.out.println(toJson(student));
		System.out.println(toJson(teacher));
		String str = "{\"name\":\"qinys\",\"age\":\"23\"}";
		String str1 = "{\"name\":\"xia\",\"age\":\"16\"}";
		System.out.println(fromJson(str,Student.class));
		System.out.println(fromJson(str1,Teacher.class));
	}
}
	class Student{
		Student(){}
		private String name;
		private String age;
		Student(String name,String age){
			this.name = name;
			this.age = age;
		}

		public String getName() {
			return name;
		}

		public String getAge() {
			return age;
		}

		@Override
		public String toString() {
			return "Student{" +
					"name='" + name + '\'' +
					", age='" + age + '\'' +
					'}';
		}
	}
	class Teacher{
		Teacher(){}
		Teacher(String name,String age){
			this.name =name;
			this.age = age;
		}
		@JsonProperty
		private String name;
		@JsonProperty
		private String age;
	}
