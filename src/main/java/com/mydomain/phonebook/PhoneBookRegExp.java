package com.mydomain.phonebook;

import com.mydomain.phonebook.exceptions.NameNotMatchException;
import com.mydomain.phonebook.exceptions.PhoneNotMatchException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneBookRegExp {
    public static boolean checkWithRegExp(String name, String surname, String phone) {
        if (phone != null && !PhoneBookRegExp.checkPhoneWithRegExp(phone)) {
            throw new PhoneNotMatchException(phone);
        }
        if (name != null && !PhoneBookRegExp.checkNameWithRegExp(name)) {
            throw new NameNotMatchException(name);
        }
        if (surname != null && !PhoneBookRegExp.checkNameWithRegExp(surname)) {
            throw new NameNotMatchException(surname);
        }

        return true;
    }

    private static boolean checkNameWithRegExp(String name) {
        Pattern p = Pattern.compile("[a-zA-Z-]*");
        Matcher m = p.matcher(name);
        return m.matches();
    }

    private static boolean checkPhoneWithRegExp(String phone) {
        Pattern p = Pattern.compile("^8[0-9]{10}$");
        Matcher m = p.matcher(phone);
        return m.matches();
    }
}
