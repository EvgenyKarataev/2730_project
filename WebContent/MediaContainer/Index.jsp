<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<div id="mediaContainerWrapper">
	<div class="row" id="mcResizeBanner">
		<span class="mcResizeText">Files</span>
	</div>
	<div id="mediaContainer">
		<div class="row" id="searchBar_tool">
			<div id="mediaSearchInputWrapper">
				<input type="text" id="mediaSearchInput"
					placeholder="Search Files..." />
			</div>

			<!-- mcTools dropdown menu -->
			<div class="dropdown" id="mcToolsDropdwon">
				<a class="dropdown-toggle" data-toggle="dropdown">
					<button id="mediaToolsBtn" title="Tools">
						<i class="icon-cog"></i>
					</button>
				</a>
				<ul class="dropdown-menu" role="menu" id="mcToolsMenu"
					aria-labelledby="dLabel">
					<li class="fileMenuItemLi"><a tabindex="-1" id="fileMenuItem">Upload Files</a></li>
					<li class="fileMenuItemLi"><a tabindex="-1" id="fileMenuItemImport">Import File</a></li>
					<li class="fileMenuItemLi"><a tabindex="-1" id="fileMenuItemDraw">Draw</a></li>
				</ul>
			</div>

		</div>
		<div class="row" id="mediaViewWrapper">
			<span id="mediaViewPositioner">
				<div class="btn-group" id="mediaViewStyle">
					<button class="mediaViewBtn btn active" id="mcListViewBtn"
						title="List View">
						<i class="icon-reorder"></i>
					</button>
					<button class="mediaViewBtn btn" id="mcGalleryViewBtn"
						title="Gallery View">
						<i class="icon-qrcode"></i>
					</button>
				</div>
				<div class="btn-group" id="mediaFilter">
					<button class="btn active" id="mcImageFilterBtn" title="Images">
						<i class="icon-picture"></i>
					</button>
					<button class="btn active" id="mcVideoFilterBtn" title="Videos">
						<i class="icon-film"></i>
					</button>
				</div>
			</span>
		</div>
		<div id="fileListContainer"></div>
	</div>
</div>