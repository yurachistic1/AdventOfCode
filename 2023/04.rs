#[allow(dead_code)]
mod utils;
use utils::read_input;

use std::collections::HashSet;

fn main() {
    let file_path = "04.in";

    let input = read_input(file_path);

    part_one_and_two(&input);
}

fn part_one_and_two(input: &Vec<String>) {
    let mut total = 0;
    let mut repeats: Vec<usize> = vec![1; input.len()];

    for (i, line) in input.iter().enumerate() {
        let scratch_card: Vec<&str> = line.split(": ").collect::<Vec<&str>>()[1]
            .split("|")
            .collect();

        let winning_nums: HashSet<String> = scratch_card[0]
            .trim()
            .split(" ")
            .filter(|s| !s.is_empty())
            .map(|num| String::from(num.trim()))
            .collect();

        let my_nums: HashSet<String> = scratch_card[1]
            .trim()
            .split(" ")
            .filter(|s| !s.is_empty())
            .map(|num| String::from(num.trim()))
            .collect();

        let overlap_cnt = winning_nums.intersection(&my_nums).count();
        if overlap_cnt > 0 {
            total += 2_i32.pow((overlap_cnt as u32) - 1);

            for j in 1..=overlap_cnt {
                repeats[i + (j as usize)] += repeats[i];
            }
        }
    }

    println!("part one: {}", total);
    println!("part two: {}", repeats.iter().sum::<usize>());
}
