type file_system = 
	| Disk of { letter : char; contents : file_system list }
	| Folder of { name : string; contents : file_system list }
	| File of { name : string }
;;

type counts_record = { folders : int; files : int };;

let counts fs =
	let rec traverse fs result =
		match fs with
		| Disk { contents; _ } -> 
			traverse_list contents result
		| Folder { contents; _ } -> 
			traverse_list contents { result with folders = result.folders + 1 }
		| File _ -> 
			{ result with files = result.files + 1 }
	
	and traverse_list fs_list result =
		match fs_list with
		| [] -> result
		| head :: tail -> 
			let updated_result = traverse head result in
			traverse_list tail updated_result
	in
	
	traverse fs { folders = 0; files = 0 }
;;

(* --------------- Testy ---------------- *)

let fs = Disk { letter = 'C'; contents = [
	Folder { name = "Program Files"; contents = [
		Folder { name = "Microsoft Office"; contents = [
			File { name = "word.exe" }
		]};
		Folder { name = "Adobe"; contents = [
			File { name = "reader.exe" }
		]}
    ]};
    Folder { name = "Users"; contents = [
		Folder { name = "Documents"; contents = [
			File { name = "note.txt" }
		]};
		File { name = "word.exe" }
    ]}
]};;

counts (Disk {letter = 'A'; contents = []});;
counts (Disk {letter = 'D'; contents = [File {name = "word.exe"}]});;
counts (Disk {letter = 'E'; contents = [Folder {name = "Users"; contents = []}]});;
counts fs;;
