package net.javenture.framework.annotation;


import java.lang.annotation.*;


@Retention(RetentionPolicy.SOURCE)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE })
public @interface NullDisallow
{
}
