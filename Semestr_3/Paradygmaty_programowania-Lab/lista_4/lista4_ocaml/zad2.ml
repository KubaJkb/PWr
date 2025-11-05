type file_system = 
	| Disk of { letter : char; contents : file_system list }
	| Folder of { name : string; contents : file_system list }
	| File of { name : string }
;;

let path fs target =
    let rec bfs queue =
		match queue with
		| [] -> None
		| (current, current_path) :: rest ->
			match current with
			| Disk { letter; contents } ->
				bfs (List.map (fun item -> (item, [Char.escaped letter ^ ":"])) contents)
          
			| Folder { name; contents } ->
				if name = target then
					Some (String.concat "\\" (List.rev current_path) ^ "\\")
				else
					bfs (rest @ List.map (fun item -> (item, name::current_path)) contents)
          
			| File { name } when name = target -> 
				Some (String.concat "\\" (List.rev current_path) ^ "\\")
          
			| _ -> bfs rest
    in
    bfs [(fs, [])]
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
    ]};
	File { name = "word.exe" };
]};;

path fs "Users";;          (* C:\\ *)
path fs "reader.exe";;     (* C:\\Program Files\\Adobe\\ *)
path fs "note.txt";;       (* C:\\Users\\Documents\\ *)
path fs "word.exe";;       (* C:\\ *)
path fs "notepad.exe";;    (* None *)
