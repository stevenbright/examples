package com.truward.langexp.typedisp.services;


import com.truward.langexp.typedisp.domain.Profile;
import com.truward.langexp.typedisp.domain.ProfilePropertyType;
import com.truward.langexp.typedisp.domain.ProfilePropertyValue;

import java.util.List;
import java.util.Map;

public interface ProfileService {
    void setProperty(long profileId, ProfilePropertyType propertyType, String value);

    void setProperty(long profileId, ProfilePropertyType propertyType, double value);

    void setProperty(long profileId, ProfilePropertyType propertyType, boolean value);

    void setProperties(long profileId, Map<ProfilePropertyType, Object> propKeyValues);


    ProfilePropertyValue getProperty(long profileId, ProfilePropertyType propertyType);


    Map<ProfilePropertyType, ProfilePropertyValue> getProperties(long profileId,
                                                                 List<ProfilePropertyType> propertyTypes);



    long getProfileID(String login, String password);

    List<Long> findProfiles(long profileID, Map<String, Object> query);



    List<Profile> getProfilesByIDs(List<Long> profileIds);
}
