[#ftl/]
[@b.head/]
[#assign keys = consts?keys?sort]
[@b.grid items=keys var="const" caption="Struts Constants"]
	[@b.row]
		[@b.col title="Index" width="5%"]${const_index+1}[/@]
		[@b.col title="Name"  style="text-align:left;padding-left:10px"]${const}[/@]
		[@b.col title="Value"]${consts[const]}[/@]
	[/@]
[/@]
[@b.foot/]