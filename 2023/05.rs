use std::fs::read_to_string;

fn main() {
    let file_path = "05.in";

    let file_content = read_to_string(file_path).unwrap();

    part_one(&file_content);
}

fn part_one(file_content: &str) {
    let input: Vec<_> = file_content
        .trim()
        .split("\n\n")
        .map(|item| {
            item.split(":").collect::<Vec<_>>()[1]
                .trim()
                .split("\n")
                .collect::<Vec<_>>()
        })
        .collect::<Vec<_>>();

    let seeds = &input[0][0];
    let maps = &input[1..];

    let mut lowest = i64::MAX;

    for seed in seeds.split(" ") {
        let mut seed: i64 = seed.parse().unwrap();

        for map in maps.iter() {
            for line in map.iter() {
                let line: Vec<i64> = line
                    .split(" ")
                    .map(|x| x.parse().expect(&format!("{:?}", line)))
                    .collect();
                let key = line[1];
                let mapping = line[0];
                let range = line[2];

                if seed >= key && seed < key + range {
                    seed = mapping + (seed - key);
                    break;
                }
            }
        }

        lowest = seed.min(lowest);
    }

    println!("part one: {lowest}");
}
