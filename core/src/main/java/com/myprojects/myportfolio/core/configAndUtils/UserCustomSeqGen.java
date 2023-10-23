package com.myprojects.myportfolio.core.configAndUtils;

import com.myprojects.myportfolio.core.dao.User;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import java.io.Serializable;

public class UserCustomSeqGen extends SequenceStyleGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        if (!(object instanceof User)) {
            throw new HibernateException(new NullPointerException());
        }

        if ((((User) object).getId()) == null) {
            return (Integer) super.generate(session, object);
        } else {
            // If id is not null, use the provided id.
            return ((User) object).getId();
        }
    }
}