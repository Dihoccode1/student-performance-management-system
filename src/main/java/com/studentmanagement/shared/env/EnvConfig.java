package com.studentmanagement.shared.env;

import io.github.cdimascio.dotenv.Dotenv;

public  final  class EnvConfig {
        private  static  final Dotenv DOTENV =Dotenv.configure().ignoreIfMissing().load();
        private EnvConfig(){};
        public static  String get(String key){
            String value = DOTENV.get(key);
            if(value == null || value.isBlank()){
                throw  new IllegalStateException("Thiếu key dữ liệu đầu vào");
            }
            return  value;
        }
}
