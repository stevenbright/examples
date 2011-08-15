

-- importing data for unit testing
insert into SIP_ACCOUNT (ID, SIP_NUMBER, SIP_PASSWORD) values (1, 'sip:100@sip.com', 'pass1');
insert into SIP_ACCOUNT (ID, SIP_NUMBER, SIP_PASSWORD) values (2, 'sip:101@sip.com', 'pass2');
insert into SIP_ACCOUNT (ID, SIP_NUMBER, SIP_PASSWORD) values (3, 'sip:102@sip.com', 'pass3');



insert into PROFILE_DATA (ID, USERNAME) values (0, 'alice');
insert into PROFILE_DATA (ID, USERNAME) values (1, 'bob');
insert into PROFILE_DATA (ID, USERNAME) values (2, 'cavin');
insert into PROFILE_DATA (ID, USERNAME) values (3, 'dave');
insert into PROFILE_DATA (ID, USERNAME) values (4, 'eva');
insert into PROFILE_DATA (ID, USERNAME) values (5, 'fred');
insert into PROFILE_DATA (ID, USERNAME) values (6, 'gloria');
insert into PROFILE_DATA (ID, USERNAME) values (7, 'harry');

insert into FRIEND (FROM_ID, TO_ID) values (0, 1);
insert into FRIEND (FROM_ID, TO_ID) values (0, 2);
insert into FRIEND (FROM_ID, TO_ID) values (2, 3);
insert into FRIEND (FROM_ID, TO_ID) values (2, 1);
insert into FRIEND (FROM_ID, TO_ID) values (5, 7);