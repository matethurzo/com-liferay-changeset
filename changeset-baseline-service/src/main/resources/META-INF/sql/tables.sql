create table BaselineEntry (
	baselineEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	baselineInformationId LONG,
	classNameId LONG,
	classPK LONG,
	version DOUBLE
);

create table BaselineInformation (
	baselineInformationId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null
);