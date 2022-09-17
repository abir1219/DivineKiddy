package com.textifly.divinekiddy.Utils

class Urls {
    companion object{
        val BASE_URL = "https://divinekiddy.com/api/"

        /**
         * @Post : POST
         * @Name : login
         * @Parameter : mobile,password
         */
        val LOGIN = BASE_URL + "login"

        /**
         * @Post : POST
         * @Name : registration
         * @Parameter : name,email,mobile,password,
         */
        val REGISTER = BASE_URL + "registration"

        /**
         * @Post : GET
         * @Name : getbanner
         * @Parameter : name,email,mobile,password,
         */
        val  BANNER = "getbanner"

        /**
         * @Post : GET
         * @Name : getallcategory
         */
        val GET_ALL_CATEGORY = BASE_URL + "getallcategory"

        /**
         * @Post : GET
         * @Name : getallsubcategory
         * @Parameter : category_id
         */
        val GET_ALL_SUB_CATEGORY = BASE_URL + "getallsubcategory"


        /**
         * @Post : POST
         * @Name : productdetails
         * @Parameter : product_id
         */
        val PRODUCT_DETAILS = BASE_URL + "productdetails"

    }
}